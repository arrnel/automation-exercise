ARG PLAYWRIGHT_VERSION="1.53.0"
FROM mcr.microsoft.com/playwright/java:v${PLAYWRIGHT_VERSION}-noble

ARG VNC_PASS

RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    libasound2t64 \
    libgtk-3-0 \
    libnotify-dev \
    x11-apps \
    x11vnc \
#    xauth \
    xvfb && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /automation-exercise
COPY . /automation-exercise

ARG DISPLAY=:0
ENV DISPLAY=${DISPLAY}
ENV XVFB_WHD=1920x1080x24
ENV XAUTHORITY=/tmp/.docker.xauth

RUN if [ -x "$(command -v node)" ]; then apt-get remove -y nodejs npm; fi

CMD xvfb-run --server-args="-screen ${DISPLAY#:} ${XVFB_WHD}" -a ./gradlew test \
    -Djunit.jupiter.extensions.autodetection.enabled=true
