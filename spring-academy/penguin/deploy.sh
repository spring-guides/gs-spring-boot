#!/usr/bin/env bash
set -o errexit
set -o nounset
set -o pipefail
if [[ "${TRACE-0}" == "1" ]]; then set -o xtrace; fi

DIR="$(dirname "$0")"

PENGUINCTL_APIURL="${PENGUINCTL_APIURL:-https://penguin-spring.test/api/v1}"
PENGUINCTL_APITOKEN="${PENGUINCTL_APITOKEN:-47f6c9c6-f6e4-4e54-ba63-c3f387ff76b9|oKxR3Lx2k78kKm2x3HECDqAU2slFBcYyXaSFQNRy}"
PENGUIN_USEDOCKER="${PENGUIN_USEDOCKER:-true}"

export PENGUINCTL_APIURL
export PENGUINCTL_APITOKEN
export PENGUIN_USEDOCKER

ci-utils() {
    if [[ "${PENGUIN_USEDOCKER:-true}" != "false" ]]; then
        docker run --rm -i -v "$(pwd)":"$(pwd)" badouralix/curl-jq "$@"
    else
        $@
    fi
}

penguinctl-docker() {
    echo "penguinctl version $(docker run --rm ghcr.io/vmware-tanzu-learning/penguinctl:latest --version)"
    docker run --rm -v "$(pwd)":"$(pwd)" ghcr.io/vmware-tanzu-learning/penguinctl:latest --url="${PENGUINCTL_APIURL}" --token="${PENGUINCTL_APITOKEN}" $@
}

penguinctl-local() {
    echo "penguinctl version $(penguinctl --version)"
    penguinctl --url="${PENGUINCTL_APIURL}" --token="${PENGUINCTL_APITOKEN}" $@
}

penguinctlcmd() {
    if [[ "${PENGUIN_USEDOCKER:-true}" != "false" ]]; then
        penguinctl-docker "$@"
    else
        penguinctl-local "$@"
    fi
}

deploy-guides() {
    echo "=== Deploying guides"
    for guideDir in "${DIR}"/guides/*; do
        echo "=== Deploying ${guideDir}"
        penguinctlcmd apply guide -f "$(pwd)/${guideDir}/guide.json"
    done
}

"$@"
