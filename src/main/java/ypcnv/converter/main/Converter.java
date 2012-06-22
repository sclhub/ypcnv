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
package ypcnv.converter.main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ypcnv.controller.ControllerContact2k3;
import ypcnv.converter.conf.DataSourceConf;
import ypcnv.exceptions.FileViewException;
import ypcnv.views.abstr.Side;
import ypcnv.views.abstr.ViewFactory;
import ypcnv.views.ifHi.DataSource;

/**
 * Converter tool. Able to import and export data between containers. 
 */
public class Converter {

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(Converter.class);

    /** Configurations of data sources to be processed. */
    private ArrayList<DataSourceConf> targets;

    /** Source of data - donor. */
    private DataSource srcObject = null ;

    /** Destination for data - acceptor. */
    private DataSource dstObject = null ;

    public Converter(ArrayList<DataSourceConf> targets) {
        this.targets = targets;
        try {
            preProcess(targets);
        } catch (Exception e) {
            String message = "Failed to create converter. Configurations are: " + targets;
            LOG.error(message);
            e.printStackTrace();
        }
    }

    /**
     * Whether or not parameters and configurations are valid. 
     * @param targets - targets configurations to be processed.
     */
    private Boolean preProcess(ArrayList<DataSourceConf> targets) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        for(DataSourceConf config : targets) {
            if(config == null) {
                LOG.error("Preprocessing failed. Have null instead of configuration.");
                return false ;
            }
            if(!config.isComplete()){
                LOG.error("Preprocessing failed. Not enough parameters.");
                return false ;
            }
        }

        DataSourceConf srcConfig = findAnyTarget(Side.source, targets);
        DataSourceConf dstConfig = findAnyTarget(Side.destination, targets);
        if (srcConfig != null) {
            srcObject = ViewFactory.get(srcConfig);
        } else {
            srcObject = null;
        }

        if (dstConfig != null) {
            dstObject = ViewFactory.get(dstConfig);
        } else {
            dstObject = null;
        }

        if ( srcObject != null && dstObject != null ) {
            return true ;
        }
        LOG.error("Preprocessing failed.");
        return false ;
    }


    /** Setter.
     * @param targets - configurations of target sources.
     */
    public void setParams(ArrayList<DataSourceConf> targets) {
        this.targets = targets;
    }
    
    /**
     * Conversion launcher.
     * @return <b>true</b> if conversion was successful, <b>false</b> - if not. 
     * @throws IOException
     * @throws FileViewException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws CloneNotSupportedException
     */
    public Boolean processConversion() throws IOException, FileViewException, SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, CloneNotSupportedException {
        if(!preProcess(targets)) {
            return false;
        }
        LOG.info("Conversion start.");
        
        ControllerContact2k3 contactBookController = new ControllerContact2k3();
        contactBookController.importData(srcObject);
        dstObject.setDataContainer(srcObject.getDataContainer());
        contactBookController.export(dstObject);

        LOG.info("Conversion done.");

        return true ;
    }
    
    /**
     * Find target configuration with specified parameters.
     * @param wantedSide - wanted side value.
     * @param targets - pool of targets to search among.
     * @return found configuration, or <b>null</b> if nothing found.
     */
    private DataSourceConf findAnyTarget(Side wantedSide, ArrayList<DataSourceConf> targets) {
        for(DataSourceConf tg : targets ) {
            if(tg.getSide().equals(wantedSide)) {
                return tg;
            }
        }
        return null ;
    }
}
