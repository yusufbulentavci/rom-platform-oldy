/* Generated By:JJTree: Do not edit this line. ASTangle_addr.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=org.apache.james.mime4j.field.address.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.bilgidoku.rom.mime4j.field.address;

public
class ASTangle_addr extends SimpleNode {
  public ASTangle_addr(int id) {
    super(id);
  }

  public ASTangle_addr(AddressListParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(AddressListParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=cd938ebefc10f435a3f4d40a148ad2d5 (do not edit this line) */
