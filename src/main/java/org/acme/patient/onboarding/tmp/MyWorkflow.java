package org.acme.patient.onboarding.tmp;

                public class MyWorkflow implements Workflow {

                    @Override
                    public String start(String language) {

                        if (language.equals("German")) {
                            return "Guten Tag!";
                        } else if (language.equals("Spanish")) {
                            return "Buenos días";
                        } else {
                            return "Good day";
                        }

                    }
                }














