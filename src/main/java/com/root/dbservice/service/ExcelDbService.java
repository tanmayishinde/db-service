package com.root.dbservice.service;

import com.root.commondependencies.vo.DefaultResponseVO;
import com.root.commondependencies.vo.ParsedDataVO;

public interface ExcelDbService {

    DefaultResponseVO saveParsedData(ParsedDataVO parsedData);

}
