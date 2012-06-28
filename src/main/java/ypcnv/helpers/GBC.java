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

/**
 * 
 */
package ypcnv.helpers;

import charva.awt.GridBagConstraints;

/**
 * Class with handy setters for <b>GridBagConstraints</b> objects from <b>charva.awt</b>.
 * <br><br>
 * This class defines constraints used for laying out components in the
 * GridBagLayout layout manager.
 */
public class GBC extends GridBagConstraints {
    
    /**
     * Just setter.
     * @param gridx - coordinate.
     * @param gridy - coordinate.
     * @param gridwidth - dimension.
     * @param gridheight - dimension.
     */
    public void setGBC(int gridx,
                          int gridy,
                          int gridwidth, 
                          int gridheight  ) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;

    }
    
}
