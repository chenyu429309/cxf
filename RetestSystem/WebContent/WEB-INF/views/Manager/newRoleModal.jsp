<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div>
				<table class="table table-bordered">
					<tr>
						<td><label for="role_name" class="col-sm-2 control-label">角色名</label>
							<input type="text" class="form-control" id="role_name"
							placeholder="角色名" /></td>
					</tr>
					<tr>
						<td>
						<label for="role_descrition"
							class="col-sm-2 control-label">描述</label> <input type="text"
							class="form-control" id="role_descrition" placeholder="描述" /></td>
					</tr>
					<tr>
						<td>
								<label for="roseParent" class="col-sm-2 control-label">上級角色</label>
									<select id="roseParent" class="form-control">
									</select>
							</td>
					</tr>
					<tr>
						<td><button  class="btn" id="saveNewRole">
							<span class="glyphicon glyphicon-ok-circle"></span>保存
						</button></td>
					</tr>
				</table>
</div>
<script src="${pageContext.request.contextPath}/assets/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/saveNewRole.js"></script>
