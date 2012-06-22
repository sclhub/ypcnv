/*
 *  Copyright 2011-2012 ASCH
 *  
 *  This file is part of YPCnv.
 *
 *  YPCnv is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  YPCnv is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with YPCnv.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */
package ypcnv.converter.conf;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ypcnv.helpers.EnumExtensions;
import ypcnv.views.abstr.DataFormatID;
import ypcnv.views.abstr.Side;


/**
 * Factory of data source objects configurations to be created from CLI or other
 * arguments.
 */
public class ConfsFactory {
    
    /** Logger. */
    private static final Log LOG = LogFactory.getLog(ConfsFactory.class);

    /** Possible CLI arguments names. */
    private enum OptionsNames implements EnumExtensions {
        help_("help"), if_("if"), of_("of"), from_("from"), to_("to");

        private String name = null ;
        OptionsNames(String name) {
            this.name = name ;
        }
        
        /** Get a string representation. */
        public String get() {
            return name ;
        }

        @Override
        public String getDisplayValue() {
            return toString();
        }
    }

    /* * */
    

    /**
     * Parser of a raw CLI arguments.
     * @param args - CLI arguments.
     * @return Mapping between arguments names and their values.
     */
    private static HashMap<String,String> parseCLIUserInput(String[] args) {
        /** Human readable options in Java mirrors. */
        HashMap<String,String> argumentsCLI = new HashMap<String,String>();

        int detectorFeedback;
        String theOptionArgument;

        LongOpt[] longopts = new LongOpt[ConfsFactoryMeta.OPTIONS_NAMES_MAP.size()];

        StringBuffer strBuffer = new StringBuffer();

        longopts[ConfsFactoryMeta.OPT_IDX_HELP] = new LongOpt(
                ConfsFactoryMeta.OPTIONS_NAMES_MAP.get(ConfsFactoryMeta.OPT_IDX_HELP), LongOpt.NO_ARGUMENT, null,
                'h');
        longopts[ConfsFactoryMeta.OPT_IDX_INPUTFILE] = new LongOpt(
                ConfsFactoryMeta.OPTIONS_NAMES_MAP.get(ConfsFactoryMeta.OPT_IDX_INPUTFILE),
                LongOpt.REQUIRED_ARGUMENT, strBuffer, 'i');
        longopts[ConfsFactoryMeta.OPT_IDX_OUTPUTFILE] = new LongOpt(
                ConfsFactoryMeta.OPTIONS_NAMES_MAP.get(ConfsFactoryMeta.OPT_IDX_OUTPUTFILE),
                LongOpt.REQUIRED_ARGUMENT, strBuffer, 'o');
        longopts[ConfsFactoryMeta.OPT_IDX_SRC_FORMAT] = new LongOpt(
                ConfsFactoryMeta.OPTIONS_NAMES_MAP.get(ConfsFactoryMeta.OPT_IDX_SRC_FORMAT),
                LongOpt.REQUIRED_ARGUMENT, strBuffer, 's');
        longopts[ConfsFactoryMeta.OPT_IDX_DST_FORMAT] = new LongOpt(
                ConfsFactoryMeta.OPTIONS_NAMES_MAP.get(ConfsFactoryMeta.OPT_IDX_DST_FORMAT),
                LongOpt.REQUIRED_ARGUMENT, strBuffer, 'd');

        Getopt getOption = new Getopt(ConfsFactory.class.getCanonicalName(), args,
                "", longopts);
        getOption.setOpterr(false); // Turn off error messages in GNU-GetOpt.

        while ((detectorFeedback = getOption.getopt()) != -1) {
            switch (detectorFeedback) {
            case ConfsFactoryMeta.CLI_ARG_IS_OPTION:
                theOptionArgument = getOption.getOptarg();

                String argumentOfOption = ((theOptionArgument != null) ? theOptionArgument
                        : "null");
                String optionName = longopts[getOption.getLongind()].getName();

                argumentsCLI.put(optionName,argumentOfOption);

                break;
            case ConfsFactoryMeta.CLI_ARG_IS_NOT_AN_OPTION:
                String messageNotAnOption = "Found CLI argument which is not an defined option.";
                LOG.error(messageNotAnOption);
                throw new IllegalArgumentException(messageNotAnOption);
            case ':':
                String messageNeedArgumentForOption = "Need additional arguments for option"
                        + (char) getOption.getOptopt();
                LOG.error(messageNeedArgumentForOption);
                throw new IllegalArgumentException(messageNeedArgumentForOption);
            case '?':
                String messageInvalidOption = "Invalid option '"
                        + args[getOption.getOptind() - 1] + "' .";
                LOG.error(messageInvalidOption);
                throw new IllegalArgumentException(messageInvalidOption);
            default:
                String messageUnexpectedValue = "By getopt() returned unexpected value '"
                        + detectorFeedback + "'.";
                LOG.error(messageUnexpectedValue);
                throw new IllegalArgumentException(messageUnexpectedValue);
            }
        }

        return argumentsCLI ;
    }
    
    /**
     * Builder of data source configurations from CLI arguments.
     * 
     * @param args
     *            - CLI arguments, "as is".
     * @return List of configurations. Quantity and complicity may depend on
     *         realization.
     */
    public static List<DataSourceConf> build(String[] args) {
        HashMap<String, String> argumentsCLI = new HashMap<String, String>();

        /** List of configurations for a data source object. */
        ArrayList<DataSourceConf> confsList = new ArrayList<DataSourceConf>();

        DataSourceConf sourceConfig = new DataSourceConf(null, null, null);
        DataSourceConf destinationConfig = new DataSourceConf(null, null, null);

        argumentsCLI = parseCLIUserInput(args);

        DataFormatID format = null;
        Object address = null;
        Side side = null;

        Iterator<String> argCLIIterator = argumentsCLI.keySet().iterator();
        while (argCLIIterator.hasNext()) {
            String arg = argCLIIterator.next();

            format = null;
            address = null;
            side = null;

            if (arg.equals(OptionsNames.from_.get())
                    || arg.equals(OptionsNames.if_.get())) {
                if (argumentsCLI.get(OptionsNames.from_.get()) != null) {
                    format = DataFormatID.valueOf(argumentsCLI
                            .get(OptionsNames.from_.get()));
                } else {
                    format = DataFormatID.none;
                }
                if (argumentsCLI.get(OptionsNames.if_.get()) != null) {
                    address = new File(argumentsCLI.get(OptionsNames.if_.get()));
                }
                side = Side.source;
                sourceConfig = new DataSourceConf(format, address, side);
            } else if (arg.equals(OptionsNames.to_.get())
                    || arg.equals(OptionsNames.of_.get())) {
                if (argumentsCLI.get(OptionsNames.to_.get()) != null) {
                    format = DataFormatID.valueOf(argumentsCLI
                            .get(OptionsNames.to_.get()));
                } else {
                    format = DataFormatID.none;
                }
                if (argumentsCLI.get(OptionsNames.of_.get()) != null) {
                    address = new File(argumentsCLI.get(OptionsNames.of_.get()));
                }
                side = Side.destination;
                destinationConfig = new DataSourceConf(format, address, side);
            }
        }

        sourceConfig.setSide(Side.source);
        destinationConfig.setSide(Side.destination);

        confsList.add(sourceConfig);
        confsList.add(destinationConfig);

        return confsList;
    }
    
}
