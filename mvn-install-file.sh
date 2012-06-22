#!/bin/bash

#
#    Copyright 2011 ASCH
#    
#    This file is part of YPCnv.
#
#    YPCnv is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    YPCnv is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with YPCnv.  If not, see <http://www.gnu.org/licenses/>.
#
##

mvnBin="/opt/maven/bin/mvn"

# How much fields per artifact
packsIdSetListBlockItemsQuantity=5
packsIdSetList=( \
"lib/cardme/cardme-v0.2.6.jar" "info.ineighborhood" "cardme" "0.2.6" "jar"
"lib/cardme/cardme-src-v0.2.6.jar" "info.ineighborhood" "cardme-src" "0.2.6" "jar"
"lib/cardme/mime-dir-j-2.1.0-1.jar" "org.ietf" "mimedir" "2.1.0-1" "jar"
"lib/poi/poi-3.8-beta4-20110826.jar" "org.apache.poi" "poi" "3.8-beta4" "jar"
"lib/poi/poi-examples-3.8-beta4-20110826.jar" "org.apache.poi" "poi-examples" "3.8-beta4" "jar"
"lib/poi/poi-excelant-3.8-beta4-20110826.jar" "org.apache.poi" "poi-excelant" "3.8-beta4" "jar"
"lib/poi/poi-ooxml-3.8-beta4-20110826.jar" "org.apache.poi" "poi-ooxml" "3.8-beta4" "jar"
"lib/poi/poi-ooxml-schemas-3.8-beta4-20110826.jar" "org.apache.poi" "poi-ooxml-schemas" "3.8-beta4" "jar"
"lib/poi/poi-scratchpad-3.8-beta4-20110826.jar" "org.apache.poi" "poi-scratchpad" "3.8-beta4" "jar"
"lib/poi/ooxml-lib/dom4j-1.6.1.jar" "dom4j" "dom4j" "1.6.1" "jar"
"lib/poi/ooxml-lib/stax-api-1.0.1.jar" "org.apache.poi" "stax-api" "1.0.1" "jar"
"lib/poi/ooxml-lib/xmlbeans-2.3.0.jar" "org.apache.poi" "xmlbeans" "2.3.0" "jar"
"lib/poi/lib/log4j-1.2.13.jar" "org.apache" "log4j" "1.2.13" "jar"
"lib/charva/charva-1.1.4.jar" "charva" "charva" "1.1.4" "jar"
"lib/charva/commons-logging-api.jar" "org.apache.commons.logging" "commons-logging-api" "0.0.0" "jar"
"lib/charva/commons-logging.jar" "org.apache.commons.logging" "commons-logging" "0.0.0" "jar"
"lib/java-gnu-getopt/java-getopt-1.0.14.jar" "gnu" "getopt" "1.0.14" "jar")

# Objects used to store artifact's information.
artifactFile=""
artifactGroupId=""
artifactId=""
artifactVersion=""
artifactPackaging=""

function loadArtifactsList {
    local idx=""

    totalArtifactQuantity=0
    for idx in $( seq 0 ${packsIdSetListBlockItemsQuantity} $(( ${#packsIdSetList[*]} - 1 )) ) ; do
        artifactFile[totalArtifactQuantity]="${packsIdSetList[idx]}"
        artifactGroupId[totalArtifactQuantity]="${packsIdSetList[idx+1]}"
        artifactId[totalArtifactQuantity]="${packsIdSetList[idx+2]}"
        artifactVersion[totalArtifactQuantity]="${packsIdSetList[idx+3]}"
        artifactPackaging[totalArtifactQuantity]="${packsIdSetList[idx+4]}"
        ((totalArtifactQuantity++))
        # debug
        #echo "${totalArtifactQuantity}|sshPort=${packsIdSetList[idx]};sshUserName=${packsIdSetList[idx+1]};sshHost=${packsIdSetList[idx+2]};sshRemoteDir=${packsIdSetList[idx+3]};sshLocalDir=${packsIdSetList[idx+4]};privateKeyFileName=;"

    done
    #echo "${totalArtifactQuantity}"
}

loadArtifactsList
for idx in $( seq 0 $(( totalArtifactQuantity - 1)) ) ; do
    if ! "${mvnBin}" install:install-file -Dfile="${artifactFile[idx]}" -DgroupId="${artifactGroupId[idx]}" -DartifactId="${artifactId[idx]}" -Dversion="${artifactVersion[idx]}" -Dpackaging="${artifactPackaging[idx]}"
    then
        echo "Error [ ${0} ]:${LINENO}: Failed to install '${artifactFile[idx]}'."
        exit 1
    fi
done

exit $?
