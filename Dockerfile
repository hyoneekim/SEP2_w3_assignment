FROM eclipse-temurin:21-jdk

RUN groupadd -r appgroup && useradd -r -g appgroup appuser

ENV DISPLAY=host.docker.internal:0.0

RUN apt-get update && \
    apt-get install -y maven wget unzip libgtk-3-0 libgbm1 libx11-6 \
        fonts-noto-cjk fonts-noto-ui-core fontconfig locales && \
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    fc-cache -fv && \
    locale-gen ja_JP.UTF-8 && \
    update-locale LANG=ja_JP.UTF-8

RUN wget https://download2.gluonhq.com/openjfx/21/openjfx-21_linux-aarch64_bin-sdk.zip -O /tmp/openjfx.zip && \
    unzip /tmp/openjfx.zip -d /opt && \
    rm /tmp/openjfx.zip && \
    cp /opt/javafx-sdk-21/lib/*.so /usr/lib/ && \
    ldconfig

ENV LANG=ja_JP.UTF-8
ENV LANGUAGE=ja_JP:ja
ENV LC_ALL=ja_JP.UTF-8

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

RUN ls -l target/

USER appuser

CMD ["sh", "-c", "java \
     -Dprism.order=sw \
     -Dprism.verbose=true \
     -Dfile.encoding=UTF-8 \
     -Djava.library.path=/opt/javafx-sdk-21/lib:/usr/lib \
     --module-path /opt/javafx-sdk-21/lib \
     --add-modules javafx.controls,javafx.fxml \
     -jar target/shopping_cart.jar"]