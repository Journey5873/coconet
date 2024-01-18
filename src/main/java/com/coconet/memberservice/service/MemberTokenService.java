package com.coconet.memberservice.service;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class MemberTokenService {
    public String getJsonValue(String jsonStr, String key) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonStr);
            return jsonObject.get(key).toString();
        }
        catch (ParseException e) {
            return "";
        }
    }

    public String decodeJwt(String jwt) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] chunks = jwt.split("\\.");
        return new String(decoder.decode(chunks[1]));
    }
}
