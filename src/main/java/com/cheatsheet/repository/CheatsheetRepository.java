package com.cheatsheet.repository;

import java.util.List;
import com.cheatsheet.model.Cheatsheet;

public interface CheatsheetRepository {
    
    // ➕ ၁။ Cheat Sheet အသစ်သိမ်းဆည်းရန်
    void addSheet(Cheatsheet sheet);
    
    // 📖 ၂။ Cheat Sheet အားလုံးကို ဆွဲထုတ်ရန်
    List<Cheatsheet> getAllSheets();
    
    // 📖 ၃။ SubCategory ID အလိုက် (ဥပမာ Java Basics, Java OOP) စာရင်း ဆွဲထုတ်ရန်
    List<Cheatsheet> getSheetsBySubId(int subId);
    
    // 🔍 ၄။ ID တစ်ခုချင်းစီအလိုက် Cheat Sheet ရဲ့ အသေးစိတ် (Detail) ကို ဆွဲထုတ်ရန်
    // 💡 ဖြေရှင်းချက်: getSheetSubById ဆိုတဲ့ နာမည်အမှားကြီးကို ဖြုတ်ပြီး ဒီ Method တစ်ခုတည်းနဲ့ပဲ အသုံးပြုပါတော့မယ်။
    Cheatsheet getSheetById(int id);
    
    // 🔎 ၅။ Keyword ဖြင့် ရှာဖွေရန်
    List<Cheatsheet> searchSheets(String keyword);
    
    // 📝 ၆။ Cheat Sheet အချက်အလက် ပြန်ပြင်ရန်
    boolean updateSheet(Cheatsheet sheet);
    
    // ❌ ၇။ Cheat Sheet ဖျက်ပစ်ရန်
    void deleteSheet(int id);
}