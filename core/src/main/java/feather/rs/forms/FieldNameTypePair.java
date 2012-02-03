package feather.rs.forms;
class FieldNameTypePair {
	private String fieldName;
	private Class<?> fieldType;
	
	public String getFieldName() {
		return fieldName;
	}
	
	public Class<?> getFieldType() {
		return fieldType;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}

	public FieldNameTypePair(String fieldName, Class<?> fieldType) {
		super();
		this.fieldName = fieldName;
		this.fieldType = fieldType;
	}
	
	
}