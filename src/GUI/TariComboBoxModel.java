package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class TariComboBoxModel extends AbstractListModel implements ComboBoxModel {
	private ArrayList<String> tari = null;
	private String selection = null;
	
	public TariComboBoxModel(List<String> tari) {
		this.tari = (ArrayList<String>) tari;
	}
	
	public Object getElementAt(int index) {
		return this.tari.get(index);
	}

	public int getSize() {
		return this.tari.size();
	}

	public Object getSelectedItem() {
		return selection;
	}

	public void setSelectedItem(Object tara) {
		selection = (String) tara;
	}

}
