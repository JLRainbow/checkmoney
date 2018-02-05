package com.citic.bill;

import java.io.IOException;
import java.util.Map;

/**
 * 下载账单接口
 * @author jial
 *
 */
public interface IBillDown {

	public Map<String, Object> billDownload(String billDate) throws IOException;
}
