package cn.com.ebmp.freesql.builder.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cn.com.ebmp.freesql.io.Resources;

/**
 * Offline entity resolver for the iBATIS DTDs
 */
public class XMLMapperEntityResolver implements EntityResolver
{

	private static final Map<String, String> doctypeMap = new HashMap<String, String>();
	private static final String FREESQL_MAPPER_DOCTYPE = "-//freesql.org//DTD Mapper 3.0//EN".toUpperCase(Locale.ENGLISH);
	private static final String FREESQL_MAPPER_URL = "http://freesql.org/dtd/freesql-3-mapper.dtd".toUpperCase(Locale.ENGLISH);

	private static final String FREESQL_CONFIG_DOCTYPE = "-//freesql.org//DTD Config 3.0//EN".toUpperCase(Locale.ENGLISH);
	private static final String FREESQL_CONFIG_URL = "http://freesql.org/dtd/freesql-3-config.dtd".toUpperCase(Locale.ENGLISH);

	private static final String FREESQL_MAPPER_DTD = "cn/com/ebmp/freesql/builder/xml/freesql-3-mapper.dtd";
	private static final String FREESQL_CONFIG_DTD = "cn/com/ebmp/freesql/builder/xml/freesql-3-config.dtd";

	static
	{
		doctypeMap.put(FREESQL_CONFIG_URL, FREESQL_CONFIG_DTD);
		doctypeMap.put(FREESQL_CONFIG_DOCTYPE, FREESQL_CONFIG_DTD);

		doctypeMap.put(FREESQL_MAPPER_URL, FREESQL_MAPPER_DTD);
		doctypeMap.put(FREESQL_MAPPER_DOCTYPE, FREESQL_MAPPER_DTD);
	}

	/**
	 * Converts a public DTD into a local one
	 * 
	 * @param publicId
	 *            Unused but required by EntityResolver interface
	 * @param systemId
	 *            The DTD that is being requested
	 * @return The InputSource for the DTD
	 * @throws org.xml.sax.SAXException
	 *             If anything goes wrong
	 */
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException
	{

		if (publicId != null)
			publicId = publicId.toUpperCase(Locale.ENGLISH);
		if (systemId != null)
			systemId = systemId.toUpperCase(Locale.ENGLISH);

		InputSource source = null;
		try
		{
			String path = doctypeMap.get(publicId);
			source = getInputSource(path, source);
			if (source == null)
			{
				path = doctypeMap.get(systemId);
				source = getInputSource(path, source);
			}
		} catch (Exception e)
		{
			throw new SAXException(e.toString());
		}
		return source;
	}

	private InputSource getInputSource(String path, InputSource source)
	{
		if (path != null)
		{
			InputStream in;
			try
			{
				in = Resources.getResourceAsStream(path);
				source = new InputSource(in);
			} catch (IOException e)
			{
			}
		}
		return source;
	}

}