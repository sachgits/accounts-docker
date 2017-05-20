FROM jboss/keycloak-mysql:3.0.0.Final

USER root
RUN yum -y install p11-kit-nss-trust openssl ca-certificates bash && yum clean all
RUN curl https://curl.haxx.se/ca/cacert-2017-01-18.pem -o /etc/pki/tls/certs/ca-bundle.crt

RUN mkdir -p /usr/share/ssl/certs
RUN cp /etc/pki/tls/certs/ca-bundle.crt /usr/share/ssl/certs/ca-bundle.crt


RUN curl -o /usr/share/ssl/certs/bundle.zip https://namecheap.simplekb.com/SiteContents/2-7C22D5236A4543EB827F3BD8936E153E/media/COMODO_DV_SHA-256_bundle.crt.zip && cd /usr/share/ssl/certs && unzip bundle.zip && rm -rf __MACOSX


RUN update-ca-trust enable
RUN update-ca-trust check
RUN update-ca-trust extract

ENV TINI_VERSION v0.14.0
ADD https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini /tini
RUN chmod +x /tini

RUN curl -o /wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
	&& chmod +x /wait-for-it.sh

USER jboss

EXPOSE 8080

ENTRYPOINT ["/tini", "--", "/wait-for-it.sh", "mysql:3306", "-t", "60", "--", "/opt/jboss/docker-entrypoint.sh"]

CMD ["-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
