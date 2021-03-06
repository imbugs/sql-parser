/**
 * Copyright © 2012 Akiban Technologies, Inc.  All rights
 * reserved.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This program may also be available under different license terms.
 * For more information, see www.akiban.com or contact
 * licensing@akiban.com.
 *
 * Contributors:
 * Akiban Technologies, Inc.
 */

package com.akiban.sql.parser;

import com.akiban.sql.StandardException;

public class GroupConcatNode extends AggregateNode
{
    private String sep;
    private OrderByList orderCols;
    
    @Override
    public void init(Object value,
                     Object aggClass,
                     Object distinct,
                     Object aggName,
                     Object orderCols,
                     Object sep)
            throws StandardException
    {
        super.init(value,
                  aggClass,
                  distinct,
                  aggName);
        
        this.orderCols = (OrderByList) orderCols;
        this.sep = (String) sep;
    }
    
    @Override
    public void copyFrom(QueryTreeNode node) throws StandardException
    {
        super.copyFrom(node);
        
        GroupConcatNode other = (GroupConcatNode) node;
        this.sep = other.sep;
        this.orderCols = (OrderByList) getNodeFactory().copyNode(other.orderCols,
                                                   getParserContext());
    }
    
    @Override
    void acceptChildren(Visitor v) throws StandardException
    {
        super.acceptChildren(v);
        
        if (orderCols != null)
            orderCols.acceptChildren(v);
    }

     /**
     * @inheritDoc
     */
    @Override
    protected boolean isEquivalent(ValueNode o) throws StandardException
    {
        if (!isSameNodeType(o))
            return false;
        
        GroupConcatNode other = (GroupConcatNode) o;
        
        return  this.sep.equals(other.sep)
             && this.orderCols.equals(other.orderCols);
    }

    @Override
    public String toString()
    {
        return super.toString() + 
               "\nseparator: " + sep +
               "\norderyByList: "+ orderCols;
                
    }
    
    public String getSeparator()
    {
        return sep;
    }
    
    public OrderByList getOrderBy()
    {
        return orderCols;
    }
}
