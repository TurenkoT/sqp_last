package com.severstal.sqp.service.impl;

/**
 * Upload service implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service
 */

import com.severstal.sqp.dao.UploadDao;
import com.severstal.sqp.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    UploadDao uploadDao;

    public Map<Integer, List<String>> parseCSV(String filename) {
        return uploadDao.parseCSV(filename);
    }


    public List<String> saveCSV(Map<Integer, List<String>> map) {
        return uploadDao.saveCSV(map);
    }

    public List<String> saveUsersCSV(Map<Integer, List<String>> map){
        return uploadDao.saveUsersCSV(map);
    }
}
