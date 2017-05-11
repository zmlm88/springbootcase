package com.zm.web.util.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.zm.web.db.model.TMenu;

/**
 * 递归数
 * 
 * @author zhumin
 *
 */
public class ModeList implements Serializable{

	private static Lock lock = new ReentrantLock();

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

	public static ModeList createTree2(List<TMenu> list,String parentId) {
		try {
			lock.lock();
			ModeList node = new ModeList();
			ArrayList<TMenu> fu = new ArrayList<TMenu>();// 用来存储parentId为空的父节点；
			ArrayList<TMenu> childs = new ArrayList<TMenu>();// 用来存储不是系统的模块
			for (TMenu tMenu : list) {
				String top = tMenu.getParentid();
				if (top.equals(parentId)) {
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return null;

	}

	public static void appendChild(ModeList node, List<TMenu> childs) {
		if (node != null) {
			String systemId = node.gettMenu().getId();
			if (childs != null && childs.size() > 0) {
				for (TMenu model : childs) {
					String parentId2 = model.getParentid();
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

}
