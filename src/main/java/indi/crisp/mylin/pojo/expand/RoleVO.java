package indi.crisp.mylin.pojo.expand;

import indi.crisp.mylin.pojo.Role;
import java.lang.Integer;
import java.lang.String;

public class RoleVO extends Role {


	public RoleVO() {
	}

	public RoleVO(String rname, String rdesc, Integer rid, String rnameZH) {
		 this.rname = rname;
		 this.rdesc = rdesc;
		 this.rid = rid;
		 this.rnameZH = rnameZH;
	}

}
