package com.defrainPhoto.pictime.constraint;

public class AddressValidator {
	
	private final String STREET_MATCHER = "\\d+[ ]([A-Za-z0-9.-])+";
	private final String CITY_MATCHER = "([A-Z][a-z]+[ ]?)+";
	private final String STATE_MATCHER = "Alabama|Alaska|Arizona|Arkansas|California|Colorado|Connecticut|Delaware|Florida|Georgia|Hawaii|\r\n" + 
			"Idaho|Illinois|Indiana|Iowa|Kansas|Kentucky|Louisiana|Maine|Maryland|Massachusetts|Michigan|\r\n" + 
			"Minnesota|Mississippi|Missouri|Montana|Nebraska|Nevada|New[ ]Hampshire|New[ ]Jersey|New[ ]Mexico\r\n" + 
			"|New[ ]York|North[ ]Carolina|North[ ]Dakota|Ohio|Oklahoma|Oregon|Pennsylvania|Rhode[ ]Island\r\n" + 
			"|South[ ]Carolina|South[ ]Dakota|Tennessee|Texas|Utah|Vermont|Virginia|Washington|West[ ]Virginia\r\n" + 
			"|Wisconsin|Wyoming";
	private final String STATE_ABRV_MATCHER = "AL|AK|AS|AZ|AR|CA|CO|CT|DE|DC|FM|FL|GA|GU|HI|ID|IL|IN|IA|KS|KY|LA|ME|MH|MD|MA|MI|MN|MS|MO|MT\r\n" + 
			"|NE|NV|NH|NJ|NM|NY|NC|ND|MP|OH|OK|OR|PW|PA|PR|RI|SC|SD|TN|TX|UT|VT|VI|VA|WA|WV|WI|WY";
	private final String ZIP_CODE_MATCHER = "\\bd{5}(-\\d{4})?\\b";
		

}
