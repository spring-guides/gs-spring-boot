#!/bin/bash

arch=$(uname -m)

if [ "$arch" = "x86_64" ]; then
	platform_arch="amd64";
elif [ "$arch" = "aarch64" ]; then
	platform_arch="arm64";
elif [ "$arch" = "arm64" ]; then
	platform_arch="arm64";
else
	echo "Unsupported platform architecture: $arch";
	exit 1;
fi

if [ -f "/tmp/workshops.yaml" ]; then
	rm /tmp/workshops.yaml
fi

for file in workshops/*/resources/workshop.yaml; do 
	echo "---" >> /tmp/workshops.yaml
	cat $file|sed "s|\$(platform_arch)|${platform_arch}|" >> /tmp/workshops.yaml
done	

kubectl apply -f /tmp/workshops.yaml

if [ -f "/tmp/workshops.yaml" ]; then
	rm /tmp/workshops.yaml
fi