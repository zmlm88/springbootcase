package com.zm.web.util.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.zm.web.cst.Const;
import com.zm.web.db.model.TMenu;

public class ModeList {

	private static ModeList instance;

	private ModeList() {
	}

	public static ModeList getInstance() {
		if (instance == null) {
			synchronized (ModeList.class) {
				if (instance == null)
					instance = new ModeList();
			}
		}
		return instance;

	}

	private TMenu tMenu;

	private List<ModeList> subList = new ArrayList<ModeList>();
	

	public List<ModeList> getSubList() {
		return subList;
	}

	public void setSubList(List<ModeList> subList) {
		this.subList = subList;
	}

	public TMenu gettMenu() {
		return tMenu;
	}

	public void settMenu(TMenu tMenu) {
		this.tMenu = tMenu;
	}

	public ModeList createTree2(List<TMenu> list) {
		ModeList node = new ModeList();
		ArrayList<TMenu> fu = new ArrayList<TMenu>();// 用来存储parentId为空的父节点；
		ArrayList<TMenu> childs = new ArrayList<TMenu>();// 用来存储不是系统的模块
		for (TMenu tMenu : list) {
			String top = tMenu.getSeat();
			if (Const.TMENU.TOP.toString().equals(top.toUpperCase())) {
				fu.add(tMenu);
			} else {
				childs.add(tMenu);
			}
		}
		for (TMenu model : fu) {
			ModeList node1 = new ModeList();
			node1.settMenu(model);
			node.subList.add(node1);
			appendChild(node1, childs);
		}
		return node;

	}

	public void appendChild(ModeList node, List<TMenu> childs) {
		if (node != null) {
			String systemId = node.gettMenu().getId();
			if (childs != null && childs.size() > 0) {
				for (TMenu model : childs) {
					String parentId2 = model.getParendids();
					if (systemId.equals(parentId2)) {
						ModeList child = new ModeList();
						child.settMenu(model);
						node.getSubList().add(child);
						appendChild(child, childs);
					}
				}
			}
		}
	}
	/**
	 * 根据id查询权限
	 */
	public List<ModeList> findMenuById(String paraentId, List<ModeList> subList){
		for(ModeList modeList:subList){
			if(modeList.gettMenu().getId().equals(paraentId)){
				return modeList.getSubList();
			}else{
				if(!StringUtils.isEmpty(modeList.getSubList()))
					findMenuById( paraentId,modeList.getSubList());
			}
		}
		return null;
	}
	
}
