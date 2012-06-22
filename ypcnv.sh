thisPath="$( dirname "${0}" )"
savedDir="$( pwd )"
cd "${thisPath}"

libTerminalPath="$( dirname "lib/libTerminal.so" )"
export LD_LIBRARY_PATH=${libTerminalPath}:${LD_LIBRARY_PATH}

test -z "TERM" \
    && export TERM=xterm

java -jar "ypcnv.jar" "${@}" 2>"ypcnv-stderr.log"
stty sane

cd "${savedDir}"
echo "Warning:$0:${LINENO}: Log file realisation may write into current but not desired directory."
