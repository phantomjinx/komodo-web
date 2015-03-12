/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.komodo.web.share;




/**
 * Application constants
 * @author mdrillin@redhat.com
 */
public class Constants {

	public static final String COMMA = ",";
	public static final String OK = "OK";
	public static final String QUESTION_MARK = "?";
	public static final String DOT = ".";
	public static final String SELECT_STAR_FROM = "SELECT * FROM";
	public static final String LIMIT_10 = "LIMIT 10";
	public static final String SPACE = " ";
	public static final String BLANK = "";
	public static final String NONE = "NONE";
	public static final String OK_BUTTON_TXT = "Ok";
	public static final String CANCEL_BUTTON_TXT = "Cancel";

	public static final String NEW_VDB_BASENAME = "NewVDB";
	
	public static final String KOMODO_WORKSPACE_SCREEN = "KomodoWorkspaceScreen";
	public static final String UNKNOWN = "[unknown]";
	
	public static final String CONFIRMATION_DIALOG = "ConfirmationDialog";
	public static final String CONFIRMATION_DIALOG_TYPE_KEY = "ConfirmDialog_TypeKey";
	public static final String CONFIRMATION_DIALOG_ARG_KEY = "ConfirmDialog_ArgKey";
    public static final String CONFIRMATION_DIALOG_DELETE_VDB = "ConfirmDeleteVDB";
    public static final String CONFIRMATION_DIALOG_CREATE_VDB = "ConfirmCreateVDB";
    
	public static final String ADD_VIEW_SRC_DIALOG = "AddViewSrcDialog";
	public static final String ADD_VIEW_SRC_AVAILABLE_SRCS = "AddViewAvailSrcs";
	public static final String UPLOAD_DRIVER_DIALOG = "UploadDriverDialog";
	
	public static final int DATASOURCES_TABLE_PAGE_SIZE = 15;
	public static final int DATASOURCE_TYPES_TABLE_PAGE_SIZE = 15;
	public static final int VDBS_TABLE_PAGE_SIZE = 15;
	public static final int VDB_MODELS_TABLE_PAGE_SIZE = 15;
	public static final int QUERY_RESULTS_TABLE_PAGE_SIZE = 15;
	public static final int QUERY_COLUMNS_TABLE_PAGE_SIZE = 6;
	
	public static final String FROM_SCREEN = "from-screen";
	
	public static final String JNDI_PREFIX = "java:/";
	public static final String JBOSS_JNDI_PREFIX = "java:jboss/datasources/";
	public static final String MODESHAPE_JNDI_PREFIX = "java:/datasources/";
	public static final String SERVICE_NAME_KEY = "service-name";
	public static final String SERVICE_VIEW_NAME = "SvcView";
	public static final String CLONE_SERVICE_KEY = "clone-service";
	public static final String DELETE_SERVICE_KEY = "delete-service";
	
    public static final String VDB_PROP_KEY_REST_AUTOGEN = "{http://teiid.org/rest}auto-generate";
    public static final String VDB_PROP_KEY_DATASERVICE_VIEWNAME = "data-service-view";
    
	public static final String OPENSHIFT_HOST_PREFIX = "[OPENSHIFT]";
	
	public static final String DYNAMIC_VDB_SUFFIX = "-vdb.xml";
	public static final String STATUS_ACTIVE = "ACTIVE";
	public static final String STATUS_INACTIVE = "INACTIVE";
	public static final String STATUS_LOADING = "LOADING";
	public static final String STATUS_UNKNOWN = "Unknown";
	
	public static String VDB = "VDB";
	public static String VIEW_MODEL = "VIEW_MODEL";
	public static String SOURCE_MODEL = "SOURCE_MODEL";
	public static String TABLE = "TABLE";
	public static String VIEW = "VIEW";
	public static String COLUMN = "COLUMN";
	public static String PARAMETER = "PARAMETER";
	public static String PROCEDURE = "PROCEDURE";
	public static String VIRTUAL_PROCEDURE = "VIRTUAL_PROCEDURE";
	public static String UNKNOWN_TYPE = "UNKNOWN";
    
}
