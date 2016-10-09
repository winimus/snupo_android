package org.snupo.android;

/**
 * Created by minim on 2016-10-02.
 */

public class BrowserTitleConverter
{
	public static String browserTitleToMid(String bt)
	{
		switch (bt)
		{
			case  "공지사항" : return "announcements";
			case "출석부" : return "attendance";
			case "회의록" : return "minutes";
			case "선거 유세" : return "elections";
			case "선곡 추천" : return "programs";
			case "앙상블 신청" : return "ensemble";
			case "레슨 신청" : return "lessons";
			case "웹사이트 이슈": return "issues";

			case "CN" : return "cn";
			case "신입생 인사" : return "greetings";
			case "객원 게시판" : return "guest";

			case "1st Violin": return "violin1";
			case "2nd Violin": return "violin2";
			case "Viola": return "viola";
			case "Cello": return "cello";
			case "Bass" : return "bass";
			case "Flute" : return "flute";
			case "Oboe/Fagott" : return "oboe";
			case "Clarinet" : return "clarinet";
			case "Brass" : return "brass";

			case "회계부" : return "accounting";
			case "홍보부" : return "pr";
			case "기획부" : return "planning";
			case "섭외부" : return "liaison";
			case "학술문화부" : return "educulture";
			case "자료관리부" : return "datamanagement";
			case "악보계" : return "librarians";
			case "임원수석" : return "board";

			case "질문과 답변" : return "qna";
			default: return bt;
		}
	}
	public static String midToBrowserTitle(String mid)
	{
		switch (mid)
		{
			case  "announcements" : return "공지사항";
			case "cn" : return "CN";
			case "violin1": return "1st Violin";
			case "violin2": return "2nd Violin";
			case "viola": return "Viola";
			case "Cello": return "Cello";
			case "bass" : return "Bass";
			case "flute" : return "Flute";
			case "oboe" : return "Oboe/Fagott";
			case "clarinet" : return "Clarinet";
			case "brass" : return "Brass";
			case "elections" : return "선거 유세";
			case "attendance" : return "출석부";
			case "minutes" : return "회의록";
			case "accounting" : return "회계부";
			case "datamanagement" : return "자료관리부";
			case "educulture" : return "학술문화부";
			case "ensemble" : return "앙상블 신청";
			case "lessons" : return "레슨 신청";
			case "greetings" : return "신입생 인사";
			case "liaison" : return "섭외부";
			case "librarians" : return "악보계";
			case "planning" : return "기획부";
			case "programs" : return "선곡 추천";
			case "pr" : return "홍보부";
			case "issues": return "웹사이트 이슈";
			case "board" : return "임원수석";
			case "guest" : return "객원";
			case "qna" : return "질문과 답변";
			default: return mid;
		}
	}
	public static String positionsToMid(int gp, int cp)
	{
		if(gp == 0)
		{
			switch (cp)
			{
				case 0:
					return "announcements";
				case 1:
					return "attendance";
				case 2:
					return "minutes";
				case 3:
					return "elections";
				case 4:
					return "programs";
				case 5:
					return "ensemble";
				case 6:
					return "lessons";
				case 7:
					return "issues";
				default:
					return "";
			}
		}
		if (gp == 1)
		{
			switch (cp)
			{
				case 0:
					return "cn";
				case 1:
					return "greetings";
				case 2:
					return "guest";
				default:
					return "";
			}
		}
		if (gp == 2)
		{
			switch (cp)
			{
				case 0:
					return "violin1";
				case 1:
					return "violin2";
				case 2:
					return "viola";
				case 3:
					return "cello";
				case 4:
					return "bass";
				case 5:
					return "flute";
				case 6:
					return "oboe";
				case 7:
					return "clarinet";
				case 8:
					return "brass";
				default:
					return "";
			}
		}
		if (gp == 3)
		{
			switch (cp)
			{
				case 0:
					return "accounting";
				case 1:
					return "pr";
				case 2:
					return "planning";
				case 3:
					return "liaison";
				case 4:
					return "educulture";
				case 5:
					return "datamanagement";
				case 6:
					return "librarians";
				case 7:
					return "board";
			}
		}
		if (gp == 4)
		{
			switch (cp)
			{
				case 0:
					return "qna";
				default:
					return "";
			}
		}
		else
			return "";
	}
}
