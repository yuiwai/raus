#!/usr/bin/env bash

aws s3 sync release s3://repo.yuiwai.com --acl public-read
