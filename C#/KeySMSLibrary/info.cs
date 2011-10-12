using System;
using System.Collections;
using System.Collections.Generic;
using System.Runtime.Serialization;
using KeySMSLibrary.Models;

namespace KeySMSLibrary
{
    [Serializable, DataContract]
    public class Info
    {
        [DataMember]
        public Account account { get; set;  }
        [DataMember]
        public User user { get; set; }
    }
}

namespace KeySMSLibrary.Models
{
    [Serializable, DataContract]
    public class Account
    {
        [DataMember]
        public String admin { get; set;  }
        [DataMember]
        public Address adress { get; set;  }        
        [DataMember]
        public String email { get; set;  }
        [DataMember]
        public String[] keywords { get; set;  }
        [DataMember]
        public String name { get; set;  }
        [DataMember]
        public String orgNumber { get; set;  }
        [DataMember]
        public String person { get; set;  }
        [DataMember]
        public String phone { get; set;  }
        [DataMember]
        public Sms sms { get; set; }
        [DataMember]
        public String type { get; set; }        
    }

    [Serializable, DataContract]
    public class Sms
    {
        [DataMember]
        public Double max { get; set;  }        
    }

    [Serializable, DataContract]
    public class Address
    {
        [DataMember]
        public String place { get; set;  }
        [DataMember]
        public String street { get; set;  }
        [DataMember]
        public String zip { get; set;  }
    }

    [Serializable, DataContract]
    public class User
    {
        [DataMember]
        public String access { get; set; }
        [DataMember]
        public String account { get; set; }
        [DataMember]
        public String email { get; set; }
        [DataMember]
        public String phone { get; set; }
        [DataMember]
        public String sender { get; set; }
        [DataMember]
        public String username { get; set; }
    }    
}