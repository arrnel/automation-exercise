ARG PLAYWRIGHT_VERSION="1.58.0"
FROM mcr.microsoft.com/playwright/java:v${PLAYWRIGHT_VERSION}-noble

ARG VNC_PASS
ENV VNC_PASS=${VNC_PASS}

RUN apt-get update && \
    apt-get install -y software-properties-common && \
    add-apt-repository universe -y && \
    apt-get update && \
    apt-get install -y \
        tigervnc-standalone-server \
        tigervnc-common \
        fluxbox \
        xterm \
        xvfb \
        dbus \
        dbus-x11 \
        websockify \
        net-tools \
        wget \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /opt/noVNC /opt/websockify && \
    wget -qO- https://github.com/novnc/noVNC/archive/v1.5.0.tar.gz | tar xz -C /opt/noVNC --strip-components=1 && \
    wget -qO- https://github.com/novnc/websockify/archive/v0.12.0.tar.gz | tar xz -C /opt/websockify --strip-components=1 && \
    ln -s /opt/noVNC/vnc.html /opt/noVNC/index.html

ENV PLAYWRIGHT_BROWSERS_PATH=/ms-playwright

WORKDIR /automation-exercise
COPY . /automation-exercise

RUN chmod +x ./entrypoint.sh ./gradlew

RUN if [ -x "$(command -v node)" ]; then apt-get remove -y nodejs npm; fi

CMD ["./entrypoint.sh"]