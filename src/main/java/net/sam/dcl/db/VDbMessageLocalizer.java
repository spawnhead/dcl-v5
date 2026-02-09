package net.sam.dcl.db;

import net.sam.dcl.db.*;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.util.ConstDefinitions;
import net.sam.dcl.util.DbReconnector;


/**
 * Localize SQL error to the appropriare message
 */
public class VDbMessageLocalizer {
	public static String localizeMessage(net.sam.dcl.db.VDbException e) {
		String res = localizeMessage(e.getErrorCode(), e.mModuleStatus, e.getMessage());
		/*if (e.executionContext != null){
					res += "\n"+e.executionContext.toString();
				}*/
		return res;
	}

	private static String localizeMessage(int errorCode, short moduleStatus, String originalMsg) {
		String msg;

		msg = ConstDefinitions.ERROR__UNKNOWN;


		if (moduleStatus == VDbConnectionManager.MODULE_STATUS__NOTDBCONNECTION) {
			msg = ConstDefinitions.ERROR__NOTDBCONNECTION;
		} else {
			switch (errorCode) {
			case-20003:
			case 1:
				msg = ConstDefinitions.ERROR__UNIQUE;
				break;
			case 2292:
				msg = ConstDefinitions.ERROR__INTEGRITY;
				break;
				//No any rows have been updated ,inserted or deleted"
			case-20001:
				msg = ConstDefinitions.ERROR__COUNT_ROWS;
				break;
			case 17011:
				msg = ConstDefinitions.ERROR__RSET_EMPTY;
				break;

				//  ORA-29902: error in executing ODCIIndexStart() routine
				//  ORA-20000: Oracle Text error:
				//  DRG-51030: wildcard query expansion resulted in too many terms
			case 29902:
				msg = ConstDefinitions.ERROR__BAD_SEARCH_CRITERIA;
				break;

			default: {
				if (DbReconnector.isError(errorCode) == true) {
					msg = ConstDefinitions.ERROR__NOTDBCONNECTION;
				}
				break;
			}
			}
		}
		return msg + ConstDefinitions.ERROR_DELIM + originalMsg;
	}
}