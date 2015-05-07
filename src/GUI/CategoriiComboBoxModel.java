package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class CategoriiComboBoxModel extends AbstractListModel implements ComboBoxModel {
	private ArrayList<String> categorii = null;
	private String selection = null;
	
	public CategoriiComboBoxModel(List<String> categorii) {
		this.categorii = (ArrayList<String>) categorii;
	}
	
	public Object getElementAt(int index) {
		return this.categorii.get(index);
	}

	public int getSize() {
		return this.categorii.size();
	}

	public Object getSelectedItem() {
		return selection;
	}

	public void setSelectedItem(Object categorie) {
		selection = (String) categorie;
	}

}
