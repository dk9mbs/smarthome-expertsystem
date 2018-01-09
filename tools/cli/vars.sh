#!/bin/bash


for i in $(dirname $0)/vars.d/*.sh; do
    . $i
done

if [ -d /etc/phl/vars.d ]; then
    for i in /etc/phl/vars.d/*.sh; do
        . $i
    done
fi

if [ -d ~/.phl/vars.d ]; then
    for i in ~/.phl/vars.d/*.sh; do
        . $i
    done
fi

