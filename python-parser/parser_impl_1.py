import re
import random
import requests
import datetime

from bs4 import BeautifulSoup

from parser_CONSTANTS import USER_AGENT_LIST as ua
import parser_mailman
import parser_utiles
import parser_logger
import parser_exceptions

from func_parse_detail      import parse_detail
from func_parse_cite_list   import parse_cite_list
from func_parse_tc_data     import parse_tc_data

class WosParser():
    def __init__(self):
        self.logger     = parser_logger.Logger('WOS_PARSER')
        self.base_url   = "http://apps.webofknowledge.com"
        self.server_url = ""

    # run 메서드
    def run(self, targetType: str, uid: str, targetURL: str):
        # HTTP 요청 및 Bytes -> BS4 #
        session = requests.Session()

        headers = session.headers
        
        rand_val    = random.random() * 10000
        user_agent  = ua[int(rand_val) % len(ua)]
        headers.update({'User-Agent': user_agent})

        http_res = session.get(targetURL)

        soup = BeautifulSoup(http_res.content, features="lxml")
        # HTTP 요청 끝 #
        
        # 페이지 처리 시작 #
        try:
            # 상세 페이지 링크
            if targetType == 'DETAIL_LINK':
                ## 상세 페이지 처리 ##
                # [0100] PHASE 시작
                self.logger.log('info', '[0100] %s PHASE started.' % targetType)


                # [0110] CITE_CNT_LINK와 논문 정보 파싱
                self.logger.log('info', '[0110] Getting "CITE_CNT_LINK" & Parsing the detail page.')
                link, paper_data = parse_detail(soup, uid)


                # [0120] 파싱한 링크 메세징, CITE_CNT_LINK 타입 파싱 요청
                self.logger.log('info', '[0130] Messaging CITE_CNT_LINK.')
                
                if paper_data['timesCited']:
                    recordState = 'IN_PROGRESS'
                    parser_mailman.send('DETAIL_LINK', 'CITE_CNT_LINK', uid, self.base_url + link, 'NONE')
                else:
                    self.logger.log('info', '[0131] Messaging CITE_CNT_LINK unecessary.')
                    recordState = 'COMPLETED'
                
                # [0130] 파싱한 정보 DB 저장 요청
                self.logger.log('info', '[0120] Requesting server to save detail data.')

                paper_data['recordState'] = recordState
                requests.post('http://127.0.0.1:9400/savePaperData', json=paper_data)
                
                ## 상세 페이지 처리 끝 ##

            # 대상을 인용 중인 논문 리스트 페이지 링크
            elif targetType == 'CITE_CNT_LINK':
                ## 피인용 리스트 페이지 처리 ##
                # [0200] PHASE 시작
                self.logger.log('info', '[0200] %s PHASE started.' % targetType)

                # [0210] 년도별 인용 횟수 정보 링크, 피인용 횟수 정보
                self.logger.log('info', '[0210] %s Parsing started.' % targetType)

                link, tc_data = parse_cite_list(soup, uid)

                # [0220] 파싱한 링크 메세징
                self.logger.log('info', '[0220] Messaging TIMES_CITED_BY_YEAR_LINK.')
                
                if link:
                    recordState = 'IN_PROGRESS'
                    parser_mailman.send('CITE_CNT_LINK', 'TIMES_CITED_BY_YEAR_LINK', uid, link, 'NONE')
                else:
                    recordState = 'COMPLETED'

                # [0230] 파싱한 정보 DB 저장 요청
                self.logger.log('info', '[0230] Requesting server to save detail data.')
                
                dto = {
                    'uid': uid,
                    'recordState': recordState,
                    'tcData': tc_data
                }
                requests.post('http://127.0.0.1:9400/saveTcData', json=dto)

                ## 인용 리스트 페이지 처리 끝 ##
                pass
            
            # 년도별 인용 횟수 정보가 있는 페이지 링크
            elif targetType == 'TIMES_CITED_BY_YEAR_LINK':
                ## TC_DATA 페이지 처리 ##
                # [0300] PHASE 시작
                self.logger.log('info', '[0300] %s PHASE started.' % targetType)

                # [0310] 년도별 인용 횟수 정보 파싱
                self.logger.log('info', '[0310] %s Parsing started.' % targetType)

                tc_data = parse_tc_data(soup, uid)

                # [0320] 파싱한 정보 DB 저장 요청
                self.logger.log('info', '[0320] Requesting server to save detail data.')
                
                dto = {
                    'uid': uid,
                    'recordState': 'COMPLETED',
                    'tcData': tc_data
                }
                requests.post('http://127.0.0.1:9400/saveTcData', json=dto)
            else:
                pass
        # 레코드 조회가 불가능한 경우
        except parser_exceptions.RecordNotAvailableError:
            self.logger.log('info', '[0140] The record is not available.')
            paper_data = {'uid': uid}
            paper_data['recordState'] = 'COMPLETED'
            requests.post('http://127.0.0.1:9400/savePaperData', json=paper_data)

        except Exception as e:
            self.logger.log('error', '[9001] Error detected. Printing http_res.')
            with open('./%s.html' % uid, 'w') as f:
                f.write(http_res.content.decode('utf-8'))
            raise e
        # 페이지 처리 끝 #

        return False
    # run 끝
    