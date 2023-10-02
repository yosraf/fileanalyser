package com.castlabs.fileanalyser.service;

import com.castlabs.fileanalyser.exception.InvalidMP4FileException;
import com.castlabs.fileanalyser.model.Box;
import com.castlabs.fileanalyser.model.BoxType;
import com.castlabs.fileanalyser.model.MediaFileAnalysisResult;
import com.castlabs.fileanalyser.provider.MediaFileProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.castlabs.fileanalyser.util.ByteArrayUtils.byteArrayToInt;
import static com.castlabs.fileanalyser.util.ByteArrayUtils.byteArrayToString;

@Service
public class MediaFileAnalysisServiceImpl implements MediaFileAnalysisService {

    private final MediaFileProvider mediaFileProvider;

    public MediaFileAnalysisServiceImpl(MediaFileProvider mediaFileProvider) {
        this.mediaFileProvider = mediaFileProvider;
    }

    @Override
    public MediaFileAnalysisResult analyseMP4(String url) {
        if (StringUtils.isEmpty(url)){
            throw new IllegalArgumentException("url could not be null or empty");
        }
        byte[] content =mediaFileProvider.fetchFileContent(url);
        int length = content.length;
        if (length < 8){
            throw new InvalidMP4FileException("MP4 file length is too short");
        }
        return new MediaFileAnalysisResult(extractBoxes(content,0,length));
    }

    private List<Box> extractBoxes(byte[] content,int position,int length){

        List<Box> boxes = new ArrayList<>();
        while (position < length) {

            int boxSize = byteArrayToInt(Arrays.copyOfRange(content, position, position + 4));
            position += 4;

            String boxType = byteArrayToString(content, position, 4);
            Box box= new Box();
            box.setSize(boxSize);
            box.setType(boxType);
            position += 4;
            if (BoxType.MOOF.name().equalsIgnoreCase(boxType) || BoxType.TRAF.name().equalsIgnoreCase(boxType)) {
               List<Box> subBoxes = extractBoxes(content, position,position + boxSize - 8);
               box.setSubBoxes(subBoxes);

            } else {
                box.setSubBoxes(Collections.emptyList());

            }
            position += boxSize - 8;
            boxes.add(box);
        }
        return boxes;
    }
}
