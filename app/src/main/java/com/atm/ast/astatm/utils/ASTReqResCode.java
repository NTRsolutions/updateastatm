package com.atm.ast.astatm.utils;

/**
 * <h4>Created</h4> 3/2/2017
 *
 * @author AST Inc.
 */
public interface ASTReqResCode {
	int REQ_PAGE_COMMUNICATOR = 101;
	int RES_PAGE_COMMUNICATOR = 102;

	int TAGS_ACTIVITY = 400;
	int SHARE_ACTIVITY = 401;
	int COMMENT_ACTIVITY = 402;
	int FILTER_RESULT = 403;
	int SEARCH_ACTIVITY = 404;

	int PERMISSION_REQ_WRITE_EXTERNAL_STORAGE = 500;
	int PERMISSION_REQ_CAMERA = 501;
	int PERMISSION_REQ_WRITE_CALENDAR = 502;
	int PERMISSION_REQ_READ_CONTACTS = 503;
	int PERMISSION_REQ_ACCESS_FINE_LOCATION = 504;
	int PERMISSION_REQ_ACCESS_COARSE_LOCATION = 505;
	int PERMISSION_REQ_READ_PHONE_STATE = 506;
	int PERMISSION_REQ_RECORD_AUDIO = 507;
	int PERMISSION_REQ_CALL_PHONE = 508;

	int CAPTURE_IMAGE = 509;

	int ATTACHMENT_REQUEST = 510;
	int ACTION_DOWNLOAD = 511;
	int ACTION_DELETE = 512;
	int VOICE_SEARCH = 513;

}
