/**
 * 
 */
package com.example.phaseiii;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 *
 */
public class Triage {
    private Triage() {}
    
    public static Triage getInstance() {
	return TriageHolder.INSTANCE;
    }
    private static class TriageHolder {
	private static final Triage INSTANCE = new Triage();
    }
}
