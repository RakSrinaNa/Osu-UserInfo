package fr.mrcraftcod.objects;

/* From http://java.sun.com/docs/books/tutorial/index.html */
/*
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */
import java.awt.event.ItemEvent;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import fr.mrcraftcod.objects.AutoComboBox.Java2sAutoTextField.AutoDocument;

@SuppressWarnings("rawtypes")
public class AutoComboBox extends JComboBox
{
	private class AutoTextFieldEditor extends BasicComboBoxEditor
	{
		AutoTextFieldEditor(List list, boolean ac)
		{
			this.editor = new Java2sAutoTextField(list, AutoComboBox.this, ac);
		}

		private Java2sAutoTextField getAutoTextFieldEditor()
		{
			return (Java2sAutoTextField) this.editor;
		}
	}

	class Java2sAutoTextField extends JTextField
	{
		class AutoDocument extends PlainDocument
		{
			private static final long serialVersionUID = 5897591998358263784L;
			private boolean autoCompletion;

			public AutoDocument(boolean ac)
			{
				super();
				this.autoCompletion = ac;
			}

			@Override
			public void insertString(int i, String s, AttributeSet attributeset) throws BadLocationException
			{
				if(!this.autoCompletion)
				{
					super.insertString(i, s, attributeset);
					return;
				}
				if(s == null || "".equals(s))
					return;
				String s1 = getText(0, i);
				if(s1.equals(""))
				{
					super.insertString(i, s, attributeset);
					return;
				}
				String s2 = getMatch(s1 + s);
				int j = i + s.length() - 1;
				if(Java2sAutoTextField.this.isStrict && s2 == null)
				{
					s2 = getMatch(s1);
					j--;
				}
				else if(!Java2sAutoTextField.this.isStrict && s2 == null)
				{
					super.insertString(i, s, attributeset);
					return;
				}
				if(Java2sAutoTextField.this.autoComboBox != null && s2 != null)
					Java2sAutoTextField.this.autoComboBox.setSelectedValue(s2);
				super.remove(0, getLength());
				super.insertString(0, s2, attributeset);
				setSelectionStart(j + 1);
				setSelectionEnd(getLength());
			}

			public boolean isAutoCompletion()
			{
				return this.autoCompletion;
			}

			@Override
			public void remove(int i, int j) throws BadLocationException
			{
				if(!this.autoCompletion)
				{
					super.remove(i, j);
					return;
				}
				int k = getSelectionStart();
				if(k > 0)
					k--;
				if(getText(0, k).equals(""))
				{
					super.remove(0, getLength());
					return;
				}
				String s = getMatch(getText(0, k));
				if(!Java2sAutoTextField.this.isStrict && s == null)
					super.remove(i, j);
				else
				{
					super.remove(0, getLength());
					super.insertString(0, s, null);
				}
				if(Java2sAutoTextField.this.autoComboBox != null && s != null)
					Java2sAutoTextField.this.autoComboBox.setSelectedValue(s);
				try
				{
					setSelectionStart(k);
					setSelectionEnd(getLength());
				}
				catch(Exception exception)
				{}
			}

			@Override
			public void replace(int i, int j, String s, AttributeSet attributeset) throws BadLocationException
			{
				super.remove(i, j);
				insertString(i, s, attributeset);
			}

			public void setAutoCompletion(boolean autoCompletion)
			{
				this.autoCompletion = autoCompletion;
			}
		}

		private static final long serialVersionUID = 6729817746756783477L;
		private List dataList;
		private boolean isCaseSensitive;
		private boolean isStrict;
		private AutoComboBox autoComboBox;

		Java2sAutoTextField(List list, AutoComboBox b, boolean ac)
		{
			this.isCaseSensitive = false;
			this.isStrict = false;
			this.autoComboBox = null;
			if(list == null)
				throw new IllegalArgumentException("values can not be null");
			this.dataList = list;
			this.autoComboBox = b;
			init(ac);
			return;
		}

		public Java2sAutoTextField(List list, boolean ac)
		{
			this.isCaseSensitive = false;
			this.isStrict = false;
			this.autoComboBox = null;
			if(list == null)
				throw new IllegalArgumentException("values can not be null");
			this.dataList = list;
			init(ac);
			return;
		}

		public List getDataList()
		{
			return this.dataList;
		}

		private String getMatch(String s)
		{
			for(int i = 0; i < this.dataList.size(); i++)
			{
				String s1 = this.dataList.get(i).toString();
				if(s1 != null)
				{
					if(!this.isCaseSensitive && s1.toLowerCase().startsWith(s.toLowerCase()))
						return s1;
					if(this.isCaseSensitive && s1.startsWith(s))
						return s1;
				}
			}
			return null;
		}

		private void init(boolean ac)
		{
			setDocument(new AutoDocument(ac));
			if(this.isStrict && this.dataList.size() > 0)
				setText(this.dataList.get(0).toString());
		}

		public boolean isCaseSensitive()
		{
			return this.isCaseSensitive;
		}

		public boolean isStrict()
		{
			return this.isStrict;
		}

		@Override
		public void replaceSelection(String s)
		{
			AutoDocument _lb = (AutoDocument) getDocument();
			if(_lb != null)
				try
				{
					int i = Math.min(getCaret().getDot(), getCaret().getMark());
					int j = Math.max(getCaret().getDot(), getCaret().getMark());
					_lb.replace(i, j - i, s, null);
				}
				catch(Exception exception)
				{}
		}

		public void setCaseSensitive(boolean flag)
		{
			this.isCaseSensitive = flag;
		}

		public void setDataList(List list)
		{
			if(list == null)
				throw new IllegalArgumentException("values can not be null");
			this.dataList = list;
			return;
		}

		public void setStrict(boolean flag)
		{
			this.isStrict = flag;
		}
	}

	private static final long serialVersionUID = 2903956019272831092L;
	private AutoTextFieldEditor autoTextFieldEditor;
	private boolean isFired;

	@SuppressWarnings({"unchecked"})
	public AutoComboBox(List list, boolean ac)
	{
		this.isFired = false;
		this.autoTextFieldEditor = new AutoTextFieldEditor(list, ac);
		setEditable(true);
		setModel(new DefaultComboBoxModel(list.toArray())
		{
			private static final long serialVersionUID = 4490030922878980301L;

			@Override
			public void addElement(Object anObject)
			{
				super.addElement(anObject);
				addDataList(anObject);
			}

			@Override
			protected void fireContentsChanged(Object obj, int i, int j)
			{
				if(!AutoComboBox.this.isFired)
					super.fireContentsChanged(obj, i, j);
			}

			@Override
			public void removeElement(Object anObject)
			{
				super.removeElement(anObject);
				removeDataList(anObject);
			}
		});
		setEditor(this.autoTextFieldEditor);
	}

	@SuppressWarnings({"unchecked"})
	public void addDataList(Object o)
	{
		List list = this.autoTextFieldEditor.getAutoTextFieldEditor().getDataList();
		if(!(list.contains(o) || o == null))
			list.add(o);
		Collections.sort(list, new ComparatorIgnoreCase<String>());
		this.autoTextFieldEditor.getAutoTextFieldEditor().setDataList(list);
		setModel(new DefaultComboBoxModel(list.toArray()));
	}

	@Override
	protected void fireActionEvent()
	{
		if(!this.isFired)
			super.fireActionEvent();
	}

	public List getDataList()
	{
		return this.autoTextFieldEditor.getAutoTextFieldEditor().getDataList();
	}

	public DefaultComboBoxModel getDefModel()
	{
		return (DefaultComboBoxModel) getModel();
	}

	public boolean isCaseSensitive()
	{
		return this.autoTextFieldEditor.getAutoTextFieldEditor().isCaseSensitive();
	}

	public boolean isStrict()
	{
		return this.autoTextFieldEditor.getAutoTextFieldEditor().isStrict();
	}

	@SuppressWarnings({"unchecked"})
	public void removeDataList(Object o)
	{
		List list = this.autoTextFieldEditor.getAutoTextFieldEditor().getDataList();
		list.remove(o);
		this.autoTextFieldEditor.getAutoTextFieldEditor().setDataList(list);
		setModel(new DefaultComboBoxModel(list.toArray()));
	}

	public void setAutoCompletion(boolean status)
	{
		((AutoDocument) this.autoTextFieldEditor.getAutoTextFieldEditor().getDocument()).setAutoCompletion(status);
	}

	public void setCaseSensitive(boolean flag)
	{
		this.autoTextFieldEditor.getAutoTextFieldEditor().setCaseSensitive(flag);
	}

	@SuppressWarnings({"unchecked"})
	public void setDataList(List list)
	{
		this.autoTextFieldEditor.getAutoTextFieldEditor().setDataList(list);
		setModel(new DefaultComboBoxModel(list.toArray()));
	}

	void setSelectedValue(Object obj)
	{
		if(this.isFired)
			return;
		this.isFired = true;
		setSelectedItem(obj);
		fireItemStateChanged(new ItemEvent(this, 701, this.selectedItemReminder, 1));
		this.isFired = false;
		return;
	}

	public void setStrict(boolean flag)
	{
		this.autoTextFieldEditor.getAutoTextFieldEditor().setStrict(flag);
	}
}