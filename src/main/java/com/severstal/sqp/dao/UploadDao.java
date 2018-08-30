package com.severstal.sqp.dao;

/**
 * Upload interface.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao
 */

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public interface UploadDao {

    Map<Integer, List<String>> parseCSV(String filename);

    List<String> saveCSV(Map<Integer, List<String>> map);

    List<String> saveUsersCSV(Map<Integer, List<String>> map);
}
