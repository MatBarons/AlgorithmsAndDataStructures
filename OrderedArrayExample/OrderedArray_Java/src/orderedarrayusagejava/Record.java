package orderedarrayusagejava;

import java.util.Objects;

/**
 *
 * @author magro
 */
public class Record {
  private String stringField = null;
  private int integerField;
  
  
  public Record(String stringField,int integerField){
    this.stringField = stringField;
    this.integerField = integerField;
  }
  
  public String getStringField(){
    return this.stringField;
  }
  
  public int getIntegerField(){
    return this.integerField;
  }
  
  
  @Override
  public boolean equals(Object rec){
    if(rec==null)
	  return false;
    if(!(rec instanceof Record))
      return false;
    if(this == rec)
    	return true;
    Record other = (Record)rec;
    return (this.stringField.equalsIgnoreCase(other.getStringField()) && 
    		this.integerField == other.getIntegerField());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.stringField.toLowerCase(),this.integerField);
  }
}

