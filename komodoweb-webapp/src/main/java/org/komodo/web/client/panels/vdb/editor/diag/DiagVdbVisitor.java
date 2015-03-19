/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package org.komodo.web.client.panels.vdb.editor.diag;

import org.komodo.spi.repository.KomodoType;
import org.komodo.web.client.resources.AppResource;
import org.komodo.web.client.services.KomodoRpcService;
import org.komodo.web.share.beans.KObjectBeanVisitor;
import org.komodo.web.share.beans.KomodoObjectBean;
import org.komodo.web.share.beans.KomodoObjectPropertyBean;
import org.modeshape.sequencer.teiid.lexicon.VdbLexicon;

/**
 *
 */
public class DiagVdbVisitor implements KObjectBeanVisitor {

    private final DiagCanvas canvas;

    private KomodoRpcService service;

    private class Context {

        private DiagNode parent;

        /**
         * @return the parent
         */
        public DiagNode getParent() {
            return this.parent;
        }

        /**
         * @param parent the parent to set
         */
        public void setParent(DiagNode parent) {
            this.parent = parent;
        }
    }

    /**
     * Create new instance
     *
     * @param canvas the canvas on which nodes/links should be added
     */
    public DiagVdbVisitor(DiagCanvas canvas) {
        this.canvas = canvas;
        this.service = KomodoRpcService.get();
    }

    private void virtualDatabase(KomodoObjectBean kObject) {
        DiagNode vdbNode = canvas.createNode(canvas.getWidth() / 2, canvas.getHeight() / 2);
        vdbNode.setImage(AppResource.INSTANCE.images().diagVdb_Image());
        KomodoObjectPropertyBean nameProperty = kObject.getProperty(VdbLexicon.Vdb.NAME);

        String vdbName = kObject.getName();
        if (nameProperty != null) {
            Object value = nameProperty.getValue();
            if (value != null)
                vdbName = value.toString();
        }

        vdbNode.setLabel(vdbName);
    }

    @Override
    public void visit(KomodoObjectBean kObject) {
        if (kObject == null)
            return;

        KomodoType type = kObject.getType();
        switch (type) {
            case ACCESS_PATTERN:
                break;
            case COLUMN:
                break;
            case DATA_TYPE_RESULT_SET:
                break;
            case FOREIGN_KEY:
                break;
            case INDEX:
                break;
            case MODEL:
                break;
            case PARAMETER:
                break;
            case PRIMARY_KEY:
                break;
            case PUSHDOWN_FUNCTION:
                break;
            case RESULT_SET_COLUMN:
                break;
            case SCHEMA:
                break;
            case STATEMENT_OPTION:
                break;
            case STORED_PROCEDURE:
                break;
            case TABLE:
                break;
            case TABULAR_RESULT_SET:
                break;
            case TEIID:
                break;
            case UNIQUE_CONSTRAINT:
                break;
            case UNKNOWN:
                break;
            case USER_DEFINED_FUNCTION:
                break;
            case VDB:
                virtualDatabase(kObject);
                break;
            case VDB_CONDITION:
                break;
            case VDB_DATA_ROLE:
                break;
            case VDB_ENTRY:
                break;
            case VDB_IMPORT:
                break;
            case VDB_MASK:
                break;
            case VDB_MODEL_SOURCE:
                break;
            case VDB_PERMISSION:
                break;
            case VDB_TRANSLATOR:
                break;
            case VIEW:
                break;
            case VIRTUAL_PROCEDURE:
                break;
            default:
                break;
        }
    }
}
