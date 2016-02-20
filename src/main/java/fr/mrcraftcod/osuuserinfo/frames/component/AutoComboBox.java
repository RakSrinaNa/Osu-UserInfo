package fr.mrcraftcod.osuuserinfo.frames.component;

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
import fr.mrcraftcod.osuuserinfo.frames.component.AutoComboBox.Java2sAutoTextField.AutoDocument;
import fr.mrcraftcod.osuuserinfo.objects.ComparatorIgnoreCase;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.event.ItemEvent;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("rawtypes") public class AutoComboBox extends JComboBox
{
	private static final long serialVersionUID = 2903956019272831092L;

	private final AutoTextFieldEditor autoTextFieldEditor;

	private boolean isFired;

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

	public class Java2sAutoTextField extends javax.swing.JTextField
	{
		private static final long serialVersionUID = 6729817746756783477L;
		private List dataList;
		private final boolean isCaseSensitive;
		private final boolean isStrict;
		private AutoComboBox autoComboBox;

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
				catch(Exception ignored)
				{
				}
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
		}

		public List getDataList()
		{
			return this.dataList;
		}

		public void setDataList(List list)
		{
			if(list == null)
				throw new IllegalArgumentException("values can not be null");
			this.dataList = list;
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
				catch(Exception ignored)
				{
				}
		}

		private String getMatch(String s)
		{
			for(Object aDataList : this.dataList)
			{
				String s1 = aDataList.toString();
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
	}

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
			public void removeElement(Object anObject)
			{
				super.removeElement(anObject);
				removeDataList(anObject);
			}

			@Override
			protected void fireContentsChanged(Object obj, int i, int j)
			{
				if(!AutoComboBox.this.isFired)
					super.fireContentsChanged(obj, i, j);
			}
		});
		setEditor(this.autoTextFieldEditor);
	}

	@SuppressWarnings({"unchecked"})
	private void addDataList(Object o)
	{
		List list = this.autoTextFieldEditor.getAutoTextFieldEditor().getDataList();
		if(!(list.contains(o) || o == null))
			list.add(o);
		Collections.sort(list, new ComparatorIgnoreCase<String>());
		this.autoTextFieldEditor.getAutoTextFieldEditor().setDataList(list);
		setModel(new DefaultComboBoxModel(list.toArray()));
	}

	public DefaultComboBoxModel getDefModel()
	{
		return (DefaultComboBoxModel) getModel();
	}

	@SuppressWarnings({"unchecked"})
	private void removeDataList(Object o)
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

	@Override
	protected void fireActionEvent()
	{
		if(!this.isFired)
			super.fireActionEvent();
	}

	private void setSelectedValue(Object obj)
	{
		if(this.isFired)
			return;
		this.isFired = true;
		setSelectedItem(obj);
		fireItemStateChanged(new ItemEvent(this, 701, this.selectedItemReminder, ItemEvent.SELECTED));
		this.isFired = false;
	}
}