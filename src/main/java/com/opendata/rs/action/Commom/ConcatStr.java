package com.opendata.rs.action.Commom;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.opendata.rs.model.RsStructure;

public class ConcatStr {
	private List<RsStructure> structures = new ArrayList<RsStructure>();  //防止Hibernate自动更新操作
	int i=0;
	String sign = "";
	public List<RsStructure> concatStr(List<RsStructure> list) throws Exception {
		for(RsStructure re : list) {
			RsStructure tmp = new RsStructure();
			BeanUtils.copyProperties(tmp,re);
			if(tmp.getParentId().trim().equals("0")) {
				tmp.setName("-"+tmp.getName());
				structures.add(tmp);
				if(re.getRsStructures()!=null && re.getRsStructures().size()!=0)
					concatStr(new ArrayList<RsStructure>(re.getRsStructures()),tmp.getId());
			}
		}
		return structures;
	}
	
	public void concatStr(List<RsStructure> list,String pid) throws Exception {
		i++;
		for (RsStructure re : list) {
			RsStructure tmp = new RsStructure();
			BeanUtils.copyProperties(tmp, re);
			sign = "-";
			for (int j = 0; j < i; j++) {
				sign += "-";
			}
			tmp.setName(sign + tmp.getName());
			structures.add(tmp);
			if (re.getRsStructures() != null
					&& re.getRsStructures().size() != 0)
				concatStr(new ArrayList<RsStructure>(re.getRsStructures()), tmp.getId());
		}
		i--;
	}
	
}
