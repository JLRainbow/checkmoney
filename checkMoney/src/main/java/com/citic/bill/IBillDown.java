package com.citic.bill;

import java.io.IOException;

/**
 * 下载账单接口
 * @author jial
 *
 */
public interface IBillDown {

	public void billDownload(String billDate) throws IOException;
}
