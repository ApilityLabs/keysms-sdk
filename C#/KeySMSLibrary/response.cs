using System;
using System.Runtime.Serialization;
using KeySMSLibrary.Models;

namespace KeySMSLibrary
{
    [Serializable, DataContract]
    public class SMSResponse
    {
        [DataMember]
        public Boolean ok { get; set; }
        [DataMember]
        public Message message { get; set; }
        [DataMember]
        public Int32 quantity { get; set; }
        [DataMember]
        public Double cost { get; set; }
        [DataMember]
        public Double smsPrice { get; set; }
    }
}

namespace KeySMSLibrary.Models
{
    [Serializable, DataContract]
    public class Message
    {
        [DataMember]
        public Boolean sent { get; set; }
        [DataMember]
        public String updated { get; set; }
        [DataMember]
        public Receiver[] receivers { get; set; }        
        [DataMember]
        public Parts parts { get; set; }
        [DataMember]
        public String created { get; set; }
        [DataMember]
        public String message { get; set; }
        [DataMember]
        public String sender { get; set; }
        [DataMember]
        public Boolean tags { get; set; }
        [DataMember]
        public String[] groups { get; set; }
        [DataMember]
        public Boolean future { get; set; }
        [DataMember]
        public Status status { get; set; }        
    }

    [Serializable, DataContract]
    public class Receiver
    {
        [DataMember]
        public String number { get; set; }
        [DataMember]
        public String prefix { get; set; }
        [DataMember]
        public String country { get; set; }
        [DataMember]
        public Int32 deliverystatus { get; set; }
        [DataMember]
        public String nextgwsynctime { get; set; }
    }

    [Serializable, DataContract]
    public class Parts
    {
        [DataMember]
        public Int32 total { get; set; }
        [DataMember]
        public String[] parts { get; set; }
    }

    [Serializable, DataContract]
    public class Status
    {
        [DataMember]
        public String value { get; set; }
        [DataMember]
        public String text { get; set; }
        [DataMember]
        public String[] aggregate { get; set; }
        [DataMember]
        public Int32 timed { get; set; }
    }
}
