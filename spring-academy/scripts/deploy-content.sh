#!/usr/bin/env bash
set -o errexit
set -o nounset
set -o pipefail
if [[ "${TRACE-0}" == "1" ]]; then set -o xtrace; fi

DIR="$(dirname "$0")"
REPO_DIR="$(realpath "${DIR}/../")"

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

deploy-all() {
    GIT_STATUS="$(git status | ci-utils jq -R -s '.' | sed 's:^.\(.*\).$:\1:' )"
    export GIT_STATUS
    export GITHUB_REF_NAME="${GITHUB_REF_NAME:-$(git branch --show-current)}"
    export GITHUB_SHA="${GITHUB_SHA:-$GIT_STATUS}"
    export GITHUB_ACTOR="${GITHUB_ACTOR:-${USER}@local}"
    DEBUG_DATE=$(date)
    export DEBUG_DATE

    envsubst < "${REPO_DIR}/course.template.json" > "${REPO_DIR}/course.json"
    penguinctlcmd apply course -f "${REPO_DIR}/course.json"
    rm "${REPO_DIR}/course.json"
}

"$@"