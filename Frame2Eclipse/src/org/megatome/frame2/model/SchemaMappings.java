package org.megatome.frame2.model;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.Frame2Config.ValidateException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SchemaMappings extends Frame2DomainObject {

	private final List<SchemaMapping> schemaMappings = new ArrayList<SchemaMapping>();

	public SchemaMappings() {
		clearComments();
	}

	@Override
	public SchemaMappings copy() {
		return new SchemaMappings(this);
	}

	private SchemaMappings(final SchemaMappings source) {
		for (SchemaMapping mapping : source.schemaMappings) {
			this.schemaMappings.add(mapping.copy());
		}

		setComments(source.getCommentMap());
	}

	// This attribute is an array, possibly empty
	public void setSchemaMapping(final SchemaMapping[] value) {
		this.schemaMappings.clear();
		if (value == null) {
			return;
		}
		for (int i = 0; i < value.length; ++i) {
			this.schemaMappings.add(value[i]);
		}
	}

	public void setSchemaMapping(final int index, final SchemaMapping value) {
		this.schemaMappings.set(index, value);
	}

	public SchemaMapping[] getSchemaMapping() {
		final SchemaMapping[] arr = new SchemaMapping[this.schemaMappings
				.size()];
		return this.schemaMappings.toArray(arr);
	}

	public List<SchemaMapping> fetchSchemaMappingList() {
		return this.schemaMappings;
	}

	public IDomainObject getSchemaMapping(final int index) {
		return this.schemaMappings.get(index);
	}

	public int addSchemaMapping(final SchemaMapping value) {
		this.schemaMappings.add(value);
		return this.schemaMappings.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeSchemaMapping(final IDomainObject value) {
		final int pos = this.schemaMappings.indexOf(value);
		if (pos >= 0) {
			this.schemaMappings.remove(pos);
		}
		return pos;
	}

	public void readNode(Node node) {
		final NodeList children = node.getChildNodes();
		int elementCount = 0;
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			final Node childNode = children.item(i);
			final String childNodeName = (childNode.getLocalName() == null ? childNode
					.getNodeName().intern()
					: childNode.getLocalName().intern());
			if (childNodeName.equals(Frame2Plugin
					.getResourceString("Frame2Model.schema-mapping"))) { //$NON-NLS-1$
				final SchemaMapping aSchemaMapping = new SchemaMapping();
				aSchemaMapping.readNode(childNode);
				this.schemaMappings.add(aSchemaMapping);
				elementCount++;
			} else {
				// Found extra unrecognized childNode
				if (childNodeName.equals(Frame2Plugin
						.getResourceString("Frame2Model.comment"))) { //$NON-NLS-1$
					recordComment(childNode, elementCount++);
				}
			}
		}
	}

	public void validate() throws ValidateException {
		for (SchemaMapping element : this.schemaMappings) {
			element.validate();
		}
	}

	public void writeNode(Writer out, String nodeName, String indent)
			throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$
		final String nextIndent = indent
				+ Frame2Plugin.getResourceString("Frame2Model.indentTabValue"); //$NON-NLS-1$
		int index = 0;
		for (SchemaMapping element : this.schemaMappings) {

			index = writeCommentsAt(out, indent, index);

			if (element != null) {
				element
						.writeNode(
								out,
								Frame2Plugin
										.getResourceString("Frame2Model.schema-mapping"), nextIndent); //$NON-NLS-1$
			}
		}

		writeRemainingComments(out, indent);
		out.write(indent);
		out
				.write(Frame2Plugin
						.getResourceString("Frame2Model.engTagStart") + nodeName + Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SchemaMappings)) {
			return false;
		}
		
		if (o == this) {
			return true;
		}
		
		SchemaMappings sm = (SchemaMappings)o;
		return (this.schemaMappings.equals(sm.schemaMappings));
	}

	@Override
	public int hashCode() {
		return this.schemaMappings.hashCode();
	}
}
