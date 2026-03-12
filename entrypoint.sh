#!/bin/bash
set -e

export PLAYWRIGHT_BROWSERS_PATH=/ms-playwright

cd /automation-exercise
chmod +x ./gradlew 2>/dev/null || true

if [ "${TEST_ENV}" != "ci" ] && [ "${ENABLE_VNC:-false}" = "true" ]; then
    echo "### Start VNC + noVNC..."

    mkdir -p /root/.vnc
    echo ${VNC_PASS} | vncpasswd -f > /root/.vnc/passwd
    chmod 600 /root/.vnc/passwd

    vncserver :1 -geometry 1920x1080 -depth 24 -localhost no -rfbport 5901 > /tmp/vnc.log 2>&1 &
    sleep 5

    export DISPLAY=:1
    fluxbox > /tmp/fluxbox.log 2>&1 &
    sleep 3

    echo "### Start websockify (noVNC)..."
    websockify --web=/opt/noVNC 6080 localhost:5901 > /tmp/websockify.log 2>&1 &

    sleep 3

else
  echo "### Skip enable VNC. ENABLE_VNC=$ENABLE_VNC"
fi

mkdir -p /var/run/dbus
dbus-daemon --system --address=unix:path=/var/run/dbus/system_bus_socket --nofork > /tmp/dbus.log 2>&1 &
sleep 1

echo "### START PLAYWRIGHT TESTS"
if [ "${ENABLE_VNC:-false}" != "true" ] && [ "${PLAYWRIGHT_HEADLESS:-false}" != "true" ]; then
    xvfb-run -a --server-args="-screen 0 1920x1080x24 -ac" \
        ./gradlew test ${TEST_ARGS}
else
    ./gradlew test ${TEST_ARGS}
fi