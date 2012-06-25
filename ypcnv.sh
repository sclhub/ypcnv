terminalBin="/usr/bin/lxterminal"
tty -s
[ ${?} -ne 0 ] \
    && "${terminalBin}" -e "$0" \
    && exit

thisPath="$( dirname "${0}" )"

libTerminalPath="$( dirname "${thisPath}/lib/libTerminal.so" )"
export LD_LIBRARY_PATH=${libTerminalPath}:${LD_LIBRARY_PATH}

test -z "${TERM}" \
    && export TERM=xterm

java -jar "${thisPath}/ypcnv.jar" "${@}" 2>"${HOME}/.ypcnv-stderr.log"
stty sane
