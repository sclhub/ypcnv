#!/bin/bash

mvnBin="mvn"

"${mvnBin}" install:install-file -Dfile=lib/cardme/cardme-v0.2.6.jar -DgroupId=info.ineighborhood -DartifactId=cardme -Dversion=0.2.6 -Dpackaging=jar
"${mvnBin}" install:install-file -Dfile=lib/cardme/cardme-src-v0.2.6.jar -DgroupId=info.ineighborhood -DartifactId=cardme-src -Dversion=0.2.6 -Dpackaging=jar
"${mvnBin}" install:install-file -Dfile=lib/cardme/mime-dir-j-2.1.0-1.jar -DgroupId=org.ietf -DartifactId=mimedir -Dversion=2.1.0-1 -Dpackaging=jar
