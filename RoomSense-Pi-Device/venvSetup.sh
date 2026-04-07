#!/bin/bash
if [ ! -d "venv" ]; then
    echo "Initializing Virtual Environment"
if [[ "$(uname -m)" == arm* || "$(uname -m)" == aarch64 ]]; then
        python3 -m venv venv --system-site-packages
    else
        python3 -m venv venv
    fi
fi

source venv/bin/activate

if [[ "$(uname -m)" == arm* ]]; then
    sudo apt update
    sudo apt install -y python3-sense-hat
fi

echo "Updating PIP"
pip install --upgrade pip

echo "Installing Dependencies"
pip install -r requirements.txt