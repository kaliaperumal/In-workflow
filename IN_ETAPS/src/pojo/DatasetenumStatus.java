package pojo;

public enum DatasetenumStatus 
{
	ReadyFound {
			public String toString() {
				return "ReadyFound";
			}
	},
	ChecksumValidation {
		public String toString() {
			return "ChecksumValidation";
		}
	},
	ZipUnavailable {
		public String toString() {
			return "ZipUnavailable";
		}
	},
	ExtractFolderUnavailable {
		public String toString() {
			return "ExtractFolderUnavailable";
		}
	},
	Fresh {
		public String toString() {
			return "Fresh";
		}
	},
	TokenSuccess {
		public String toString() {
			return "TokenSuccess";
		}
	},
	TokenFailure {
		public String toString() {
			return "TokenFailure";
		}
	},
	ZipExtractError {
		public String toString() {
			return "ZipExtractError";
		}
	},
	XMLDTDandIDReplacementError {	
		public String toString() {
			return "XMLDTDandIDReplacementError";
		}
	},
	ValidationSuccess {
		public String toString() {
			return "Validation Success";
		}
	},
	ValidationFailure {
		public String toString() {
			return "Validation Failure";
		}
	},
	GenerateOPT {
		public String toString() {
			return "Generate OPT";
		}
	},
	MailSend {
		public String toString() {
			return "Mail Send";
		}
	},
	MailNotSend {
		public String toString() {
			return "MailNotSend";
		}
	},
	XMLSignalSend {
		public String toString() {
			return "XMLSignalSend";
		}		
	},
	
	XSLTConversionFailed {
		public String toString() {
			return "XSLTConversionFailed";
		}
	} ,
	XSLTConversionSuccess {
		public String toString() {
			return "XSLTConversionSuccess";
		}
	}
}
