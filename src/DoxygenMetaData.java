/*!
 * \file
 * \brief Only meta data for Doxygen documentation handler and nothing more valuable.
 */
/**
 * \mainpage YPCnv about
 * 
 * \section intro Introduction
 * 
 * This program name is "YPCnv", or "ypcnv", it is abbreviation and should be
 * unwraped as "Yellow Pages Converter".
 * 
 * This is yet another one more tool to work with phonebook entries. Once I
 * tried to deploy my phonebook inside my new device. I did a try to evacuate
 * contacts from MS based hand held device. It was not easy to get my data back
 * from MS technology, and I got an idea of this tool, or toolbox. Also the tool
 * is aimed to help with batch processing of v-card files, in case when an
 * acceptor device did not fully (in an way obvious for a human) support them.
 * 
 * Just now is implemented conversion of "visit card" objects from one format to
 * another.
 * 
 * \section install Installation and quick start for *nix like platforms.
 * 
 * \subsection step1 Step 1:
 * 
 * Unpack archive 'ypcnv-yyMMdd-bin.tar.bz2' into any directory of your choice.
 * Here letters set 'yyMMdd' designates numeric version of YPCnv.
 * 
 * \subsection step2 Step 2:
 * 
 * Run shell script 'ypcnv'. Set 'executable' access flag if you need it.
 * 
 * \subsection step3 Step 3:
 * 
 * You may use 'ypcnv.desktop' file, packaged with YPCnv, to set up a button in
 * a launch panel or a toolbar.
 * 
 * \section Miscellaneous
 * 
 * \subsection UI_details UI details
 * 
 * YPCnv UI based on CharVA framework and depends on libTerminal library. The
 * library is packed inside archive with YPCnv, launcher script shall pick it up
 * properly.
 * 
 * Currently *nix like environments are supported. On other platforms you may
 * need to download appropriate for your platform libTerminal library from
 * CharVA's web site (<a
 * href="http://www.pitman.co.za/projects/charva/">www.pitman.co.za</a>) and
 * setup it's location. Then you may launch 'ypcnv.jar' as a regular Java
 * program.
 * 
 * Run YPCnv in terminal emulator. Such as LXTerminal, PuTTY or similar. Check
 * CharVA's FAQ on it's site. You may need to check system environment variable
 * 'TERM', if it was not set by your system "out of the box".
 * 
 * \subsection Hand_assisted_launch "Hand" assisted launch
 * 
 * On unsupported platforms or owing to other reasons one may want to do "hand"
 * assisted launch. Here is an example, commands using LXTerminal on *nix like
 * system:
 * 
 * <pre>
 * &nbsp;&nbsp;export LD_LIBRARY_PATH=directory_containing_libTerminal.so:$LD_LIBRARY_PATH
 * &nbsp;&nbsp;lxterminal -e "java -jar ypcnv.jar"
 * </pre>
 * 
 * Or in a single line:
 * 
 * <pre>
 * &nbsp;&nbsp;lxterminal -e "bash -c \"export LD_LIBRARY_PATH=directory_containing_libTerminal.so:${LD_LIBRARY_PATH} && java -jar ypcnv.jar\\""
 * </pre>
 * 
 * \section Copyright_and_licence Copyright and licence
 * 
 * Copyright 2011&nbsp;-&nbsp;2012 ASCH
 * 
 * YPCnv is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * YPCnv is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * YPCnv. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

/*!
 * \brief Only meta data for Doxygen documentation handler and nothing more valuable. 
 */
class DoxygenMetaData {
}
