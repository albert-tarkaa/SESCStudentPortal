FROM ubuntu:latest
LABEL authors="tenece"

ENTRYPOINT ["top", "-b"]