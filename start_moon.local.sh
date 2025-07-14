#!/bin/bash

minikube_tunnel_service_exists() {
    systemctl list-units --full --all | grep -Fq "minikube-tunnel.service"
}

create_minikube_tunnel_service() {
    echo "🔄 Создание сервиса minikube-tunnel..."
    sudo tee /etc/systemd/system/minikube-tunnel.service > /dev/null <<EOF
[Unit]
Description=Minikube Tunnel Service
After=network.target

[Service]
Type=simple
User=$(whoami)
ExecStart=$(which minikube) tunnel
Restart=on-failure
RestartSec=5s

[Install]
WantedBy=multi-user.target
EOF

    sudo systemctl daemon-reload
    sudo systemctl enable minikube-tunnel
    echo "✅ Сервис minikube-tunnel создан и добавлен в автозагрузку"
}


set -e  # Прерывать скрипт при ошибках

# Парсинг аргументов
while [[ $# -gt 0 ]]; do
    case "$1" in
        --port=*)
            PORT="${1#*=}"
            ;;
        --cpus=*)
            CPUS="${1#*=}"
            ;;
        --memory=*)
            MEMORY="${1#*=}"
            ;;
        --disk-size=*)
            DISK_SIZE="${1#*=}"
            ;;
        --is_driver_docker=*)
            IS_DRIVER_DOCKER="${1#*=}"
            ;;
        *)
            echo "⚠️ Неизвестный параметр: $1"
            echo "Использование: $0 [--port=8080] [--cpus=4] [--memory=8G] [--disk-size=20G] [--is_enabled=true/false]"
            exit 1
            ;;
    esac
    shift
done

# Установка значений по умолчанию
PORT=${PORT:-8080}
CPUS=${CPUS:-4}
MEMORY=${MEMORY:-8G}
DISK_SIZE=${DISK_SIZE:-20G}
IS_DRIVER_DOCKER=$(echo ${IS_DRIVER_DOCKER:-false} | tr '[:upper:]' '[:lower:]')

sudo systemctl stop minikube-tunnel 2>/dev/null || echo "Сервис minikube-tunnel отсутствует"
if minikube_tunnel_service_exists; then
  sudo kubectl apply -f moon-local.yaml
  sudo systemctl disable minikube-tunnel
fi

minikube delete --all --purge

# Проверка IS_ENABLED
if [[ "$IS_DRIVER_DOCKER" != "true" && "$IS_DRIVER_DOCKER" != "false" ]]; then
    echo "❌ ERROR: '--is_driver_docker' should equals true/false. Default value: false. Provided value: '$IS_ENABLED'" >&2
    exit 1
fi

# Проверка minikube и helm
if ! command -v minikube &> /dev/null; then
    echo "❌ minikube should be installed"
    exit 1
fi

if ! command -v kubectl &> /dev/null; then
    echo "❌ kubectl should be installed"
    exit 1
fi

echo "Start minikube"
if [[ $IS_DRIVER_DOCKER == "true" ]]; then
  minikube start --cpus=$CPUS --memory=$MEMORY --disk-size=$DISK_SIZE --driver=docker kvm2
else
  minikube start --cpus=$CPUS --memory=$MEMORY --disk-size=$DISK_SIZE --driver kvm2
fi

echo "Start moon"
kubectl apply -f ./moon-local.yaml

echo "Assign ip address to LoadBalancer"
if [[ $IS_DRIVER_DOCKER == true ]]; then
  if ! minikube_tunnel_service_exists; then
    create_minikube_tunnel_service
  fi
  echo "Selenium URL for tests: http://localhost:4444/wd/hub"
  echo "Moon web interface: http://localhost:8080/#"
else
  minikube kubectl -- patch svc moon -n moon --patch "{\"spec\": {\"externalIPs\":[\"$(minikube ip)\"]}}"
  MINIKUBE_IP=$(echo minikube ip)
  echo "Selenium URL for tests: http://$MINIKUBE_IP:4444/wd/hub"
  echo "Moon web interface: http://$MINIKUBE_IP:8080/#"
fi

echo "Show moon pods"
kubectl get pods -n moon
