#!/bin/bash

mvn package

elasticsearch/bin/plugin --remove covauth
elasticsearch/bin/plugin --url file:///Users/mschwartz/workspace/elasticsearch-plugin-covauth/target/releases/covauth-1.0.0-SNAPSHOT.zip --install covauth

elasticsearch/bin/elasticsearch
